import React, { useState } from 'react';
import * as XLSX from 'xlsx';
import { JhiItemCount, JhiPagination } from 'react-jhipster';
import { Table, Button, Progress } from 'reactstrap';
import axios from 'axios';

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

function SaleContractImport() {
  const [excelData, setExcelData] = useState<string[][]>([]);
  const [columns, setColumns] = useState<string[]>([]);

  const requiredColumns = [
    'STT',
    'contractId',
    'companyId',
    'accountId',
    'contactSignedDate',
    'contactSignedTitle',
    'contractEndDate',
    'contractNumber',
    'contractNumberInput',
    'contractStageId',
    'contractStartDate',
    'ownerEmployeeId',
    'paymentMethodId',
    'contractName',
    'contractTypeId',
    'currencyId',
    'grandTotal',
    'paymentTermId',
    'quoteId',
    'currencyExchangeRateId',
    'contractStageName',
    'paymentStatusId',
    'period',
    'payment',
  ];

  const [paginationState, setPaginationState] = useState({
    activePage: 1,
    itemsPerPage: 10,
  });

  const [uploading, setUploading] = useState(false);
  const [uploadProgress, setUploadProgress] = useState(0); // Tiến trình %

  const totalItems = excelData.length;

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = (event: ProgressEvent<FileReader>) => {
      const result = event.target?.result;
      if (!result || typeof result === 'string') {
        console.error('File read error or unsupported file type.');
        return;
      }

      const data = new Uint8Array(result);
      const workbook = XLSX.read(data, { type: 'array' });
      const sheetName = workbook.SheetNames[0];
      const sheet = workbook.Sheets[sheetName];

      const jsonData = XLSX.utils.sheet_to_json<string[]>(sheet, { header: 1 });
      if (jsonData.length > 0) {
        const fileColumns = jsonData[0];

        // Kiểm tra số lượng cột
        if (fileColumns.length !== requiredColumns.length) {
          alert('File không đúng định dạng. Vui lòng dùng file đúng mẫu.');
          return;
        }

        // Kiểm tra tên các cột
        for (let i = 0; i < requiredColumns.length; i++) {
          if (fileColumns[i] !== requiredColumns[i]) {
            alert(`Cột thứ ${i + 1} phải là '${requiredColumns[i]}', nhưng tìm thấy '${fileColumns[i]}'`);
            return;
          }
        }

        // Nếu tất cả cột hợp lệ
        setColumns(fileColumns);
        setExcelData(jsonData.slice(1));
      } else {
        alert('File rỗng hoặc không có dữ liệu.');
      }
    };
    reader.readAsArrayBuffer(file);
  };

  const convertToList = (): ISaleContract[] => {
    return excelData.map(row => ({
      contractId: row[1] || null,
      companyId: row[2] || null,
      accountId: row[3] || null,
      contactSignedDate: row[4] || null,
      contactSignedTitle: row[5] || null,
      contractEndDate: row[6] || null,
      contractNumber: row[7] || null,
      contractNumberInput: row[8] || null,
      contractStageId: row[9] || null,
      contractStartDate: row[10] || null,
      ownerEmployeeId: row[11] || null,
      paymentMethodId: row[12] || null,
      contractName: row[13] || null,
      contractTypeId: Number(row[14]) || null,
      currencyId: row[15] || null,
      grandTotal: Number(row[16]) || null,
      paymentTermId: row[17] || null,
      quoteId: row[18] || null,
      currencyExchangeRateId: row[19] || null,
      contractStageName: row[20] || null,
      paymentStatusId: Number(row[21]) || null,
      period: Number(row[22]) || null,
      payment: row[23] || null,
    }));
  };

  const handlePushToCloud = async () => {
    if (uploading) return; // tránh nhấn nhiều lần

    const companyList = convertToList();
    const totalRecords = companyList.length;
    if (totalRecords === 0) {
      alert('Không có dữ liệu để upload.');
      return;
    }

    setUploading(true);
    setUploadProgress(0);

    const batchSize = 1000;
    const totalBatches = Math.ceil(totalRecords / batchSize);

    for (let batchIndex = 0; batchIndex < totalBatches; batchIndex++) {
      const start = batchIndex * batchSize;
      const end = Math.min(start + batchSize, totalRecords);
      const batchData = companyList.slice(start, end);

      try {
        // Sử dụng axios
        const response = await axios.post('/api/sale-contracts/batch', batchData);
        // Nếu response.status ngoài 2xx, axios sẽ ném lỗi, nên không cần kiểm tra thủ công

        // Batch upload thành công, cập nhật tiến trình
        const uploadedCount = end; // số dòng đã upload xong
        const progressPercent = Math.floor((uploadedCount / totalRecords) * 100);
        setUploadProgress(progressPercent);
      } catch (error) {
        console.error('Lỗi upload:', error);
        alert('Upload thất bại!');
        setUploading(false);
        return;
      }
    }

    // Tất cả batch upload xong
    alert('Upload thành công tất cả dữ liệu!');
    setUploading(false);
  };

  const handlePagination = (currentPage: number) => {
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });
  };

  // Cắt dữ liệu để hiển thị theo phân trang
  const startIndex = (paginationState.activePage - 1) * paginationState.itemsPerPage;
  const endIndex = startIndex + paginationState.itemsPerPage;
  const paginatedData = excelData.slice(startIndex, endIndex);

  return (
    <div>
      <label htmlFor="file-upload" className="btn btn-info me-2">
        <i className="pi pi-file-import" style={{ fontSize: '1rem' }}></i>
        <span className="ms-1" style={{ color: 'white', fontWeight: 'bold' }}>
          import Data
        </span>
      </label>
      <input id="file-upload" type="file" accept=".xls,.xlsx" onChange={handleFileChange} style={{ display: 'none' }} />

      {excelData.length > 0 && (
        <Button
          className="me-2"
          color="info"
          onClick={() => {
            void handlePushToCloud();
          }}
          disabled={uploading}
        >
          <i className="pi pi-cloud-upload" style={{ fontSize: '1rem' }}></i>
          <span className="ms-1" style={{ color: 'white', fontWeight: 'bold' }}>
            start push Data
          </span>
        </Button>
      )}

      {uploading && (
        <div style={{ width: '300px', marginTop: '20px' }}>
          <Progress value={uploadProgress} />
          <span>{uploadProgress}%</span>
        </div>
      )}

      {excelData.length > 0 && (
        <div style={{ marginTop: '20px' }}>
          <Table responsive>
            <thead>
              <tr>
                {columns.map((col, i) => (
                  <th key={i} className="hand">
                    {col}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {paginatedData.map((row, rowIndex) => (
                <tr key={rowIndex}>
                  {row.map((cell, cellIndex) => (
                    <td key={cellIndex}>{cell !== undefined ? cell.toString() : ''}</td>
                  ))}
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      )}

      {totalItems ? (
        <div className={paginatedData && paginatedData.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
}

export default SaleContractImport;
