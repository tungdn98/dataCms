export interface IProduct {
  id?: number;
  productId?: string | null;
  productCode?: string | null;
  productFamilyId?: string | null;
  productPriceId?: string | null;
  productName?: string | null;
  productFamilyCode?: string | null;
  productFamilyName?: string | null;
}

export class ProductImport {
  STT: number | null = null;
  productId: string | null = null;
  productCode: string | null = null;
  productFamilyId: string | null = null;
  productPriceId: string | null = null;
  productName: string | null = null;
  productFamilyCode: string | null = null;
  productFamilyName: string | null = null;
}

export const defaultValue: Readonly<IProduct> = {};
