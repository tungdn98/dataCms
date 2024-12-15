export interface IPaymentMethod {
  id?: number;
  paymentStatusId?: string | null;
  paymentStatusName?: string | null;
}

export class PaymentMethodImport {
  STT: number | null = null;
  paymentStatusId: string | null = null;
  paymentStatusName: string | null = null;
}

export const defaultValue: Readonly<IPaymentMethod> = {};
