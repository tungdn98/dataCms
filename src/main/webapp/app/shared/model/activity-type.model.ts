export interface IActivityType {
  id?: number;
  activityTypeId?: number | null;
  activityType?: string | null;
  textStr?: string | null;
}

export const defaultValue: Readonly<IActivityType> = {};
