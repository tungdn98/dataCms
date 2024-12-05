export interface IContractType {
  id?: number;
  contractTypeId?: string | null;
  contractTypeName?: string | null;
  contractTypeCode?: string | null;
}

export const defaultValue: Readonly<IContractType> = {};
