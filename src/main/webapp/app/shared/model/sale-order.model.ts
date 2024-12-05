import dayjs from 'dayjs';

export interface ISaleOrder {
  id?: number;
  orderId?: string | null;
  contractId?: string | null;
  ownerEmployeeId?: string | null;
  productId?: string | null;
  totalValue?: number | null;
  orderStageId?: string | null;
  orderStageName?: string | null;
  createdDate?: string | null;
  createdBy?: string | null;
  lastModifiedDate?: string | null;
  lastModifiedBy?: string | null;
}

export const defaultValue: Readonly<ISaleOrder> = {};
