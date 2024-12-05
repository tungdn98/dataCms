import dayjs from 'dayjs';

export interface ICompany {
  id?: number;
  companyCode?: string | null;
  companyName?: string | null;
  description?: string | null;
  location?: string | null;
  phoneNumber?: string | null;
  createdDate?: string | null;
  createdBy?: string | null;
  lastModifiedDate?: string | null;
  lastModifiedBy?: string | null;
}

export const defaultValue: Readonly<ICompany> = {};
