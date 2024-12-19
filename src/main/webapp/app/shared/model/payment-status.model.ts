export interface IPaymentStatus {
  id?: number;
  paymentStatusId?: string | null;
  paymentStatusName?: string | null;
}

export const defaultValue: Readonly<IPaymentStatus> = {};
