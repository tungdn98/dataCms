export interface IOpportunityStage {
  id?: number;
  opportunityStageId?: string | null;
  opportunityStageName?: string | null;
  opportunityStageCode?: string | null;
}

export class OpportunityStageImport {
  STT: number | null = null;
  opportunityStageId: string | null = null;
  opportunityStageName: string | null = null;
  opportunityStageCode: string | null = null;
}

export const defaultValue: Readonly<IOpportunityStage> = {};
