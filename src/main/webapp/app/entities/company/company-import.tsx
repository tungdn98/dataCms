import React, { useState } from 'react';
import * as XLSX from 'xlsx';
import { JhiItemCount, JhiPagination } from 'react-jhipster';
import { Table, Button, Label } from 'reactstrap'; // Giả sử bạn có sẵn các component này

function CompanyImport() {
  const [excelData, setExcelData] = useState<string[][]>([]);
  const [columns, setColumns] = useState<string[]>([]);

  const requiredColumns = ['STT', 'companyCode', 'companyName', 'description', 'location', 'phoneNumber'];

  const [paginationState, setPaginationState] = useState({
    activePage: 1,
    itemsPerPage: 10,
  });

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

  const handlePushToCloud = () => {
    fetch('/api/uploadExcelData', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ data: excelData }),
    })
      .then(response => response.json())
      .then(() => {
        alert('Upload thành công!');
      })
      .catch(err => {
        console.error(err);
        alert('Upload thất bại!');
      });
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
          {' '}
          import Data{' '}
        </span>
      </label>
      <input id="file-upload" type="file" accept=".xls,.xlsx" onChange={handleFileChange} style={{ display: 'none' }} />
      {excelData.length > 0 && (
        <Button className="me-2" color="info" onClick={handlePushToCloud}>
          <i className="pi pi-cloud-upload" style={{ fontSize: '1rem' }}></i>
          <span className="ms-1" style={{ color: 'white', fontWeight: 'bold' }}>
            {' '}
            start push Data{' '}
          </span>
        </Button>
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

export default CompanyImport;
