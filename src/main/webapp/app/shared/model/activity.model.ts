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

export class IActivityImport {
  STT: number | null = null;
  activityId?: string | null = null;
  companyId?: string | null = null;
  createDate?: string | null = null;
  deadline?: string | null = null;
  name?: string | null = null;
  state?: string | null = null;
  type?: string | null = null;
  accountId?: string | null = null;
  activityTypeId?: string | null = null;
  objectTypeId?: string | null = null;
  priorityId?: string | null = null;
  opportunityId?: string | null = null;
  orderId?: string | null = null;
  contractId?: string | null = null;
  priorityName?: string | null = null;
  responsibleId?: string | null = null;
  startDate?: string | null = null;
  closedOn?: string | null = null;
  duration?: number | null = null;
  durationUnitId?: string | null = null;
  conversion?: number | null = null;
  textStr?: string | null = null;
}

export const defaultValue: Readonly<IActivity> = {};
