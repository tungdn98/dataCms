import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Card, CardBody, CardTitle, CardText, Button, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

const menuItems = [
  {
    title: 'My workspace',
    description: 'You frequently open this',
    icon: <i className="fa fa-user-circle-o fa-4x" aria-hidden="true" />, // Ví dụ icon
    link: '/my-workspace', // Thay đổi đường dẫn
  },
  {
    title: 'Hệ thống báo cáo thông minh',
    description: 'My. Nguyen Viet featured this',
    icon: <i className="fa fa-bar-chart fa-4x" aria-hidden="true" />, // Ví dụ icon
    link: '/he-thong-bao-cao', // Thay đổi đường dẫn
  },
  {
    title: 'CMCC CRM: Báo cáo hợp đồng bán hàng',
    description: 'You frequently open this',
    icon: <i className="fa fa-file-text-o fa-4x" aria-hidden="true" />, // Ví dụ icon
    link: '/cmcc-crm', // Thay đổi đường dẫn
  },
];

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div>
      <h5>Recommended</h5> {/* Thêm tiêu đề */}
      <Row className="mt-4">
        {' '}
        {/* Thêm margin-top */}
        {menuItems.map((item, index) => (
          <Col md="4" key={index} className="mb-4">
            {' '}
            {/* Điều chỉnh số cột và thêm margin-bottom */}
            <Card>
              <CardBody className="text-center">
                {' '}
                {/* Căn giữa nội dung */}
                {item.icon}
                <CardTitle tag="h5" className="mt-3">
                  {item.title}
                </CardTitle>
                <CardText>{item.description}</CardText>
                <Link to={item.link}>
                  {' '}
                  {/* Sử dụng Link để điều hướng */}
                  <Button color="primary">Open</Button>
                </Link>
              </CardBody>
            </Card>
          </Col>
        ))}
      </Row>
      {account?.login ? (
        <div>
          <Alert color="success">You are logged in as user {account.login}.</Alert>
        </div>
      ) : (
        <div></div>
      )}
    </div>
  );
};

export default Home;
