import dayjs from 'dayjs';
import { IRoleGroup } from 'app/shared/model/role-group.model';
import { IEmpGroup } from 'app/shared/model/emp-group.model';

export interface IRoles {
  id?: number;
  resourceUrl?: string;
  resourceDesc?: string | null;
  createdDate?: string | null;
  createdBy?: string | null;
  lastModifiedDate?: string | null;
  lastModifiedBy?: string | null;
  roleGroup?: IRoleGroup | null;
  empGroups?: IEmpGroup[] | null;
}

export const defaultValue: Readonly<IRoles> = {};
