export interface IOpportunityStageReason {
  id?: number;
  opportunityStageReasonId?: string | null;
  opportunityStageId?: string | null;
  opportunityStageReasonName?: string | null;
}

export class OpportunityStageReasonImport {
  STT: number | null = null;
  opportunityStageReasonId: string | null = null;
  opportunityStageId: string | null = null;
  opportunityStageReasonName: string | null = null;
}

export const defaultValue: Readonly<IOpportunityStageReason> = {};
