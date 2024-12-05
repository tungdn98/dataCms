import dayjs from 'dayjs';
import { IRoles } from 'app/shared/model/roles.model';

export interface IRoleGroup {
  id?: number;
  groupName?: string | null;
  createdDate?: string | null;
  createdBy?: string | null;
  lastModifiedDate?: string | null;
  lastModifiedBy?: string | null;
  roles?: IRoles[] | null;
}

export const defaultValue: Readonly<IRoleGroup> = {};
