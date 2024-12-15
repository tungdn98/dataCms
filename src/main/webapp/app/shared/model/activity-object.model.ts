export interface IActivityObject {
  id?: number;
  unitCode?: string | null;
  unitName?: string | null;
}

export const defaultValue: Readonly<IActivityObject> = {};
