export interface IPaymentStatus {
  id?: number;
  paymentStatusId?: string | null;
  paymentStatusName?: string | null;
}

export class PaymentStatusImport {
  STT: number | null = null;
  paymentStatusId: string | null = null;
  paymentStatusName: string | null = null;
}

export const defaultValue: Readonly<IPaymentStatus> = {};
