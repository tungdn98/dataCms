export interface IActivityType {
  id?: number;
  activityTypeId?: number | null;
  activityType?: string | null;
  textStr?: string | null;
}


export class ActivityTypeImport {
  STT: number | null = null;
  activityTypeId: string | null = null;
  activityType: string | null = null;
  textStr: string | null = null;
}


export const defaultValue: Readonly<IActivityType> = {};
