import dayjs from 'dayjs';
import { IEmpGroup } from 'app/shared/model/emp-group.model';

export interface IEmployee {
  id?: number;
  employeeCode?: string | null;
  employeeName?: string;
  username?: string;
  password?: string | null;
  active?: number | null;
  companyCode?: string | null;
  companyName?: string | null;
  organizationId?: string | null;
  employeeLastName?: string | null;
  employeeMiddleName?: string | null;
  employeeTitleId?: string | null;
  employeeTitleName?: string | null;
  employeeFullName?: string | null;
  createdDate?: string | null;
  createdBy?: string | null;
  lastModifiedDate?: string | null;
  lastModifiedBy?: string | null;
  empGroup?: IEmpGroup | null;
}

export const defaultValue: Readonly<IEmployee> = {};
