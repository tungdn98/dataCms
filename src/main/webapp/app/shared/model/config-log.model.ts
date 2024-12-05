import dayjs from 'dayjs';

export interface IConfigLog {
  id?: number;
  configName?: string | null;
  valueBefore?: string | null;
  valueAfter?: string | null;
  modifiedDate?: string | null;
  modifiedUsername?: string | null;
  modifiedFullname?: string | null;
}

export const defaultValue: Readonly<IConfigLog> = {};
