export interface IOpportunityStage {
  id?: number;
  opportunityStageId?: string | null;
  opportunityStageName?: string | null;
  opportunityStageCode?: string | null;
}

export const defaultValue: Readonly<IOpportunityStage> = {};
