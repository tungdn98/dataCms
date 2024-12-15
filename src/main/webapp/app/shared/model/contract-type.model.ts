export interface IContractType {
  id?: number;
  contractTypeId?: string | null;
  contractTypeName?: string | null;
  contractTypeCode?: string | null;
}

export class ContractTypeImport {
  STT: number | null = null;
  contractTypeId: string | null = null;
  contractTypeName: string | null = null;
  contractTypeCode: string | null = null;
}

export const defaultValue: Readonly<IContractType> = {};
