import dayjs from 'dayjs';

export interface ISaleOpportunity {
  id?: number;
  opportunityId?: number | null;
  opportunityCode?: string;
  opportunityName?: string;
  opportunityTypeName?: string | null;
  startDate?: string | null;
  closeDate?: string | null;
  stageId?: number | null;
  stageReasonId?: number | null;
  employeeId?: number | null;
  leadId?: number | null;
  currencyCode?: string | null;
  accountId?: number | null;
  productId?: number | null;
  salesPricePrd?: number | null;
  value?: number | null;
}

export const defaultValue: Readonly<ISaleOpportunity> = {};
