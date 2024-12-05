import dayjs from 'dayjs';
import { IEmployee } from 'app/shared/model/employee.model';
import { IRoles } from 'app/shared/model/roles.model';

export interface IEmpGroup {
  id?: number;
  groupName?: string;
  createdDate?: string | null;
  createdBy?: string | null;
  lastModifiedDate?: string | null;
  lastModifiedBy?: string | null;
  employees?: IEmployee[] | null;
  roles?: IRoles[] | null;
}

export const defaultValue: Readonly<IEmpGroup> = {};
