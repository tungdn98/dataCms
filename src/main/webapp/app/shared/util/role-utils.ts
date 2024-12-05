import { Storage } from 'react-jhipster';
/**
 * Util function to check user have a role or not
 *
 * @param role role to check
 */
export const HaveRole = (role: string) => {
  const roles = Storage.session.get('jhi-roles');

  if (roles) {
    return roles.indexOf(role) > -1;
  }

  return false;
};
