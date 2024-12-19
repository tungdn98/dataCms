export interface ICurrency {
  id?: number;
  currencyId?: string | null;
  currencyNum?: string | null;
  currencyCode?: string | null;
  currencyName?: string | null;
  currencyExchangeRateId?: number | null;
  conversionRate?: number | null;
}

export const defaultValue: Readonly<ICurrency> = {};
