import axios, { AxiosResponse } from 'axios';
import { Storage } from 'react-jhipster';
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { serializeAxiosError } from './reducer.utils';

import { AppThunk } from 'app/config/store';

const AUTH_TOKEN_KEY = 'jhi-authenticationToken';
const AUTH_ROLES_KEY = 'jhi-roles';

export const initialState = {
  loading: false,
  isAuthenticated: false,
  loginSuccess: false,
  loginError: false,
  showModalLogin: false,
  account: {} as any,
  errorMessage: null as unknown as string,
  redirectMessage: null as unknown as string,
  sessionHasBeenFetched: false,
  logoutUrl: null as unknown as string,
};

export type AuthenticationState = Readonly<typeof initialState>;

// Actions

export const getSession = (): AppThunk => (dispatch, getState) => {
  dispatch(getAccount());
};

export const getAccount = createAsyncThunk('authentication/get_account', async () => axios.get<any>('api/account'), {
  serializeError: serializeAxiosError,
});

interface IAuthParams {
  username: string;
  password: string;
  rememberMe?: boolean;
}

export const authenticate = createAsyncThunk(
  'authentication/login',
  async (auth: IAuthParams) => axios.post<any>('api/authenticate', auth),
  {
    serializeError: serializeAxiosError,
  }
);

export const login: (username: string, password: string, rememberMe?: boolean) => AppThunk =
  (username, password, rememberMe = false) =>
  async dispatch => {
    const result = await dispatch(authenticate({ username, password, rememberMe }));
    const response = result.payload as AxiosResponse;
    const bearerToken = response?.headers?.authorization;
    if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
      const jwt = bearerToken.slice(7, bearerToken.length);
      if (rememberMe) {
        Storage.local.set(AUTH_TOKEN_KEY, jwt);
      } else {
        Storage.session.set(AUTH_TOKEN_KEY, jwt);
      }
    }
    // Cập nhật quyền sau khi đăng nhập thành công
    const roles = response?.data?.authorities || [];
    Storage.session.set(AUTH_ROLES_KEY, roles); // Lưu quyền vào sessionStorage
    dispatch(getSession());
    extendTokenExpiry();
  };

export const clearAuthToken = () => {
  if (Storage.local.get(AUTH_TOKEN_KEY)) {
    Storage.local.remove(AUTH_TOKEN_KEY);
    Storage.local.remove(AUTH_ROLES_KEY);
  }
  if (Storage.session.get(AUTH_TOKEN_KEY)) {
    Storage.session.remove(AUTH_TOKEN_KEY);
    Storage.session.remove(AUTH_ROLES_KEY);
  }

  // Ghi vào localStorage để thông báo đăng xuất
  localStorage.setItem('logout-event', Date.now().toString());
};

export const logout: () => AppThunk = () => dispatch => {
  clearAuthToken();
  dispatch(logoutSession());
};

export const clearAuthentication = messageKey => dispatch => {
  clearAuthToken();
  dispatch(authError(messageKey));
  dispatch(clearAuth());
};

export const AuthenticationSlice = createSlice({
  name: 'authentication',
  initialState: initialState as AuthenticationState,
  reducers: {
    logoutSession() {
      return {
        ...initialState,
        showModalLogin: true,
      };
    },
    authError(state, action) {
      return {
        ...state,
        showModalLogin: true,
        redirectMessage: action.payload,
      };
    },
    clearAuth(state) {
      return {
        ...state,
        loading: false,
        showModalLogin: true,
        isAuthenticated: false,
      };
    },
  },
  extraReducers(builder) {
    builder
      .addCase(authenticate.rejected, (state, action) => ({
        ...initialState,
        errorMessage: action.error.message,
        showModalLogin: true,
        loginError: true,
      }))
      .addCase(authenticate.fulfilled, state => ({
        ...state,
        loading: false,
        loginError: false,
        showModalLogin: false,
        loginSuccess: true,
      }))
      .addCase(getAccount.rejected, (state, action) => ({
        ...state,
        loading: false,
        isAuthenticated: false,
        sessionHasBeenFetched: true,
        showModalLogin: true,
        errorMessage: action.error.message,
      }))
      .addCase(getAccount.fulfilled, (state, action) => {
        const isAuthenticated = action.payload && action.payload.data && action.payload.data.activated;
        if (isAuthenticated) {
          const roles = action.payload.data.authorities;
          Storage.session.set(AUTH_ROLES_KEY, roles);
        }
        return {
          ...state,
          isAuthenticated,
          loading: false,
          sessionHasBeenFetched: true,
          account: action.payload.data,
        };
      })
      .addCase(authenticate.pending, state => {
        state.loading = true;
      })
      .addCase(getAccount.pending, state => {
        state.loading = true;
      });
  },
});

export const { logoutSession, authError, clearAuth } = AuthenticationSlice.actions;

// Reducer
export default AuthenticationSlice.reducer;

// Gia hạn thời gian lưu trữ token
const extendTokenExpiry = () => {
  const token = Storage.local.get(AUTH_TOKEN_KEY) || Storage.session.get(AUTH_TOKEN_KEY);
  if (token) {
    const expirationTime = Date.now() + 5 * 60 * 1000; // 5 phút
    localStorage.setItem('token-expiration', expirationTime.toString());
  }
};

// Kiểm tra thời gian hết hạn token
const checkTokenExpiry = () => {
  const expirationTime = localStorage.getItem('token-expiration');
  if (expirationTime && Date.now() > parseInt(expirationTime, 10)) {
    clearAuthToken();
    // window.location.href = '/login'; // Hoặc hành động khác tuỳ vào logic của ứng dụng
  }
};

// Lắng nghe sự kiện người dùng để gia hạn thời gian lưu trữ token
const setupTokenExpiryListeners = () => {
  document.addEventListener('mousemove', extendTokenExpiry);
  document.addEventListener('keypress', extendTokenExpiry);
  window.addEventListener('storage', event => {
    if (event.key === 'logout-event') {
      // Xóa token và thực hiện các hành động đăng xuất khác
      clearAuthToken();
      // window.location.href = '/login'; // Hoặc hành động khác tuỳ vào logic của ứng dụng
    }
  });
};

// Khởi tạo các sự kiện và kiểm tra token khi ứng dụng tải
window.onload = () => {
  setupTokenExpiryListeners();
  extendTokenExpiry();
  setInterval(checkTokenExpiry, 10000); // Kiểm tra token mỗi 10 giây
};
