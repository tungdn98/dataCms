import React from 'react';
import { Badge } from 'primereact/badge';
export const StatusMappingHTML = {
  '00': <Badge value="Chờ phê duyệt" severity="warning"></Badge>,
  '01': <Badge value="Hoạt động" severity="success"></Badge>,
  '02': <Badge value="Không hoạt động" severity="danger"></Badge>,
  '03': <Badge value="Đã đóng" severity="danger"></Badge>,
};

export const StatusEmployeeMappingHTML = {
  '1': <Badge value="Hoạt động" severity="success"></Badge>,
  '0': <Badge value="Không hoạt động" severity="danger"></Badge>,
};

export const StatusMappingRaw = {
  '00': 'Chờ phê duyệt',
  '01': 'Hoạt động ',
  '02': 'Không hoạt động',
};
