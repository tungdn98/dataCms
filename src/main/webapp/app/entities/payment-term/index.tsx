import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PaymentTerm from './payment-term';
import PaymentTermDetail from './payment-term-detail';
import PaymentTermUpdate from './payment-term-update';
import PaymentTermDeleteDialog from './payment-term-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PaymentTermUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PaymentTermUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PaymentTermDetail} />
      <ErrorBoundaryRoute path={match.url} component={PaymentTerm} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PaymentTermDeleteDialog} />
  </>
);

export default Routes;
