import React, { useState } from 'react';
import * as XLSX from 'xlsx';
import { JhiItemCount, JhiPagination } from 'react-jhipster';
import { Table, Button, Progress } from 'reactstrap';
import axios from 'axios';

interface ImportProps<T> {
  model: new () => T; // Class model để khởi tạo đối tượng
  endpoint: string; // API endpoint để upload dữ liệu
}

function ImportComponent<T>({ model, endpoint }: ImportProps<T>) {
  const [excelData, setExcelData] = useState<string[][]>([]);
  const [columns, setColumns] = useState<string[]>([]);
  const [paginationState, setPaginationState] = useState({
    activePage: 1,
    itemsPerPage: 10,
  });
  const [uploading, setUploading] = useState(false);
  const [uploadProgress, setUploadProgress] = useState(0);

  // Lấy các key từ class model để làm tiêu đề cột
  const requiredColumns = Object.keys(new model());
  const totalItems = excelData.length;

  // Hàm chuyển đổi dữ liệu Excel sang danh sách đối tượng
  const convertToList = (): T[] => {
    return excelData.map(row => {
      const obj: Partial<T> = {};

      requiredColumns.forEach((key, index) => {
        let value: string | number | null = row[index]; // Cho phép cả string, number và null

        // Nếu giá trị là số, chuyển đổi
        if (value !== null && !isNaN(Number(value))) {
          value = Number(value);
        }

        // Ép kiểu giá trị qua unknown trước khi gán
        obj[key as keyof T] = (value || null) as unknown as T[keyof T];
      });

      return obj as T;
    });
  };

  // Xử lý file Excel khi người dùng chọn file
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
        console.log(fileColumns.length);
        console.log(requiredColumns.length);
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
        setExcelData(jsonData.slice(1)); // Bỏ dòng tiêu đề
      } else {
        alert('File rỗng hoặc không có dữ liệu.');
      }
    };
    reader.readAsArrayBuffer(file);
  };

  // Upload dữ liệu lên server theo batch
  const handlePushToCloud = async () => {
    if (uploading) return;

    const dataList = convertToList();
    const totalRecords = dataList.length;
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
      const batchData = dataList.slice(start, end);

      try {
        await axios.post(endpoint, batchData);
        const uploadedCount = end;
        const progressPercent = Math.floor((uploadedCount / totalRecords) * 100);
        setUploadProgress(progressPercent);
      } catch (error) {
        console.error('Lỗi upload:', error);
        alert('Upload thất bại!');
        setUploading(false);
        return;
      }
    }

    alert('Upload thành công tất cả dữ liệu!');
    setUploading(false);
  };

  const handlePagination = (currentPage: number) => {
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });
  };

  const startIndex = (paginationState.activePage - 1) * paginationState.itemsPerPage;
  const endIndex = startIndex + paginationState.itemsPerPage;
  const paginatedData = excelData.slice(startIndex, endIndex);

  return (
    <div>
      <label htmlFor="file-upload" className="btn btn-info me-2">
        <i className="pi pi-file-import" style={{ fontSize: '1rem' }}></i>
        <span className="ms-1" style={{ color: 'white', fontWeight: 'bold' }}>
          Import Data
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
            Start Push Data
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

export default ImportComponent;
