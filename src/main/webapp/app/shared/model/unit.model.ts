export interface IUnit {
  id?: number;
  unitCode?: string | null;
  unitName?: string | null;
}

export class UnitImport {
  STT: number | null = null;
  unitCode: string | null;
  unitName: string | null;
}

export const defaultValue: Readonly<IUnit> = {};
