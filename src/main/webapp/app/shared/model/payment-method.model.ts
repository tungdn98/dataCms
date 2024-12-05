export interface IPaymentMethod {
  id?: number;
  paymentStatusId?: string | null;
  paymentStatusName?: string | null;
}

export const defaultValue: Readonly<IPaymentMethod> = {};
