export interface ICustomer {
  id?: number;
  accountId?: string | null;
  accountCode?: string | null;
  accountName?: string | null;
  mappingAccount?: string | null;
  accountEmail?: string | null;
  accountPhone?: string | null;
  accountTypeName?: string | null;
  genderName?: string | null;
  industryName?: string | null;
  ownerEmployeeId?: number | null;
}

export const defaultValue: Readonly<ICustomer> = {};
