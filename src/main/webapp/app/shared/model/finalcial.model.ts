export interface IFinalcial {
  id?: number;
  code?: string | null;
  customerName?: string | null;
  customerShortName?: string | null;
  customerType?: string | null;
}

export class FinalcialImport {
  STT: number | null = null;
  code: string | null = null;
  customerName: string | null = null;
  customerShortName: string | null = null;
  customerType: string | null = null;
}

export const defaultValue: Readonly<IFinalcial> = {};
