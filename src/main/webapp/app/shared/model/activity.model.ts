import dayjs from 'dayjs';

export interface IActivity {
  id?: number;
  activityId?: string | null;
  companyId?: string | null;
  createDate?: string | null;
  deadline?: string | null;
  name?: string | null;
  state?: string | null;
  type?: string | null;
  accountId?: string | null;
  activityTypeId?: string | null;
  objectTypeId?: string | null;
  priorityId?: string | null;
  opportunityId?: string | null;
  orderId?: string | null;
  contractId?: string | null;
  priorityName?: string | null;
  responsibleId?: string | null;
  startDate?: string | null;
  closedOn?: string | null;
  duration?: number | null;
  durationUnitId?: string | null;
  conversion?: number | null;
  textStr?: string | null;
}

export const defaultValue: Readonly<IActivity> = {};
