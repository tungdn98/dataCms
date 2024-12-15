export interface IPaymentTerm {
  id?: number;
  paymentTermId?: string | null;
  paymentTermCode?: string | null;
  paymentTermName?: string | null;
}


export class PaymentTermImport {
  STT: number | null = null;
  paymentTermId: string | null = null;
  paymentTermCode: string | null = null;
  paymentTermName: string | null = null;
}

export const defaultValue: Readonly<IPaymentTerm> = {};
