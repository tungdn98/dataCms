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

export const defaultValue: Readonly<IProduct> = {};
