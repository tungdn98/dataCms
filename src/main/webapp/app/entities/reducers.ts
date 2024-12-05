import config from 'app/entities/config/config.reducer';
import configLog from 'app/entities/config-log/config-log.reducer';
import empGroup from 'app/entities/emp-group/emp-group.reducer';
import employee from 'app/entities/employee/employee.reducer';
import loginHistory from 'app/entities/login-history/login-history.reducer';
import roleGroup from 'app/entities/role-group/role-group.reducer';
import roles from 'app/entities/roles/roles.reducer';
import company from 'app/entities/company/company.reducer';
import saleOrder from 'app/entities/sale-order/sale-order.reducer';
import saleOpportunity from 'app/entities/sale-opportunity/sale-opportunity.reducer';
import saleContract from 'app/entities/sale-contract/sale-contract.reducer';
import product from 'app/entities/product/product.reducer';
import paymentTerm from 'app/entities/payment-term/payment-term.reducer';
import opportunityStage from 'app/entities/opportunity-stage/opportunity-stage.reducer';
import opportunityStageReason from 'app/entities/opportunity-stage-reason/opportunity-stage-reason.reducer';
import employeeLead from 'app/entities/employee-lead/employee-lead.reducer';
import customer from 'app/entities/customer/customer.reducer';
import currency from 'app/entities/currency/currency.reducer';
import contractType from 'app/entities/contract-type/contract-type.reducer';
import activity from 'app/entities/activity/activity.reducer';
import activityType from 'app/entities/activity-type/activity-type.reducer';
import activityObject from 'app/entities/activity-object/activity-object.reducer';
import paymentStatus from 'app/entities/payment-status/payment-status.reducer';
import paymentMethod from 'app/entities/payment-method/payment-method.reducer';
import unit from 'app/entities/unit/unit.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  config,
  configLog,
  empGroup,
  employee,
  loginHistory,
  roleGroup,
  roles,
  company,
  saleOrder,
  saleOpportunity,
  saleContract,
  product,
  paymentTerm,
  opportunityStage,
  opportunityStageReason,
  employeeLead,
  customer,
  currency,
  contractType,
  activity,
  activityType,
  activityObject,
  paymentStatus,
  paymentMethod,
  unit,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
