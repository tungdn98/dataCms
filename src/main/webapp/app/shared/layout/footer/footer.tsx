import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = () => (
  <footer className="footer">
    <Row className="align-items-center">
      <Col md="4">
        <span className="copyright">&copy; {new Date().getFullYear()}</span>
      </Col>
      <Col md="4" className="text-center">
        <ul className="social-links list-inline">
          <li className="list-inline-item">
            <a href="https://www.facebook.com/your-facebook-page" target="_blank" rel="noopener noreferrer">
              <i className="pi pi-facebook" style={{ fontSize: '1.2rem' }}></i>
            </a>
          </li>
          <li className="list-inline-item">
            <a href="https://twitter.com/your-twitter-profile" target="_blank" rel="noopener noreferrer">
              <i className="pi pi-twitter" style={{ fontSize: '1.2rem' }}></i>
            </a>
          </li>
          <li className="list-inline-item">
            <a href="https://www.linkedin.com/company/your-linkedin-page" target="_blank" rel="noopener noreferrer">
              <i className="pi pi-linkedin" style={{ fontSize: '1.2rem' }}></i>
            </a>
          </li>
        </ul>
      </Col>
      <Col md="4" className="text-end">
        <ul className="footer-links list-inline">
          <li className="list-inline-item">
            <a href="/terms">Terms of Service</a>
          </li>
          <li className="list-inline-item">
            <a href="/privacy">Privacy Policy</a>
          </li>
        </ul>
      </Col>
    </Row>
  </footer>
);

export default Footer;
