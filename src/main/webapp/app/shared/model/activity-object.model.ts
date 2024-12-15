export interface IActivityObject {
  id?: number;
  unitCode?: string | null;
  unitName?: string | null;
}

export class ActivityObjectImport {
  STT: number | null = null;
  unitCode: string | null = null;
  unitName?: string | null;
}
export const defaultValue: Readonly<IActivityObject> = {};
