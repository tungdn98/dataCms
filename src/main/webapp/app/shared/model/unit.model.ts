export interface IUnit {
  id?: number;
  unitCode?: string | null;
  unitName?: string | null;
}

export const defaultValue: Readonly<IUnit> = {};
