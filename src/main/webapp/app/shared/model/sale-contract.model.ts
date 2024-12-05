import dayjs from 'dayjs';

export interface ISaleContract {
  id?: number;
  contractId?: string | null;
  companyId?: string | null;
  accountId?: string | null;
  contactSignedDate?: string | null;
  contactSignedTitle?: string | null;
  contractEndDate?: string | null;
  contractNumber?: string | null;
  contractNumberInput?: string | null;
  contractStageId?: string | null;
  contractStartDate?: string | null;
  ownerEmployeeId?: string | null;
  paymentMethodId?: string | null;
  contractName?: string | null;
  contractTypeId?: number | null;
  currencyId?: string | null;
  grandTotal?: number | null;
  paymentTermId?: string | null;
  quoteId?: string | null;
  currencyExchangeRateId?: string | null;
  contractStageName?: string | null;
  paymentStatusId?: number | null;
  period?: number | null;
  payment?: string | null;
}

export const defaultValue: Readonly<ISaleContract> = {};
