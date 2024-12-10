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

export class CustomerImport {
  STT: number | null = null;
  leadId?: string | null = null;
  accountId?: string | null = null;
  accountCode?: string | null = null;
  accountName?: string | null = null;
  mappingAccount?: string | null = null;
  accountEmail?: string | null = null;
  accountPhone?: string | null = null;
  accountTypeName?: string | null = null;
  genderName?: string | null = null;
  industryName?: string | null = null;
  ownerEmployeeId?: string | null = null;
}

export const defaultValue: Readonly<ICustomer> = {};
