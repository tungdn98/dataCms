import dayjs from 'dayjs';

export interface IConfig {
  id?: number;
  configName?: string | null;
  configValue?: string;
  configDesc?: string | null;
  createdDate?: string | null;
  createdBy?: string | null;
  lastModifiedDate?: string | null;
  lastModifiedBy?: string | null;
}

export const defaultValue: Readonly<IConfig> = {};
