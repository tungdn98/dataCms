export interface IPaymentTerm {
  id?: number;
  paymentTermId?: string | null;
  paymentTermCode?: string | null;
  paymentTermName?: string | null;
}

export const defaultValue: Readonly<IPaymentTerm> = {};
