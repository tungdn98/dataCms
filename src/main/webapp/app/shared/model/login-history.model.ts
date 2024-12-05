import dayjs from 'dayjs';

export interface ILoginHistory {
  id?: number;
  empCode?: string | null;
  empUsername?: string | null;
  empFullName?: string | null;
  loginIp?: string | null;
  loginTime?: string | null;
}

export const defaultValue: Readonly<ILoginHistory> = {};
