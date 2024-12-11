import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        {/*<span className="hipster rounded" />*/}
      </Col>
      <Col md="9">
        <h2>Welcome to the admin page!</h2>
        <span className="lead">How is your day going. There are a lot of cool things on the menu, check them out.</span>
        {account?.login ? (
          <div>
            <Alert color="success">You are logged in as user {account.login}.</Alert>
          </div>
        ) : (
          <div></div>
        )}
      </Col>
    </Row>
  );
};

export default Home;
