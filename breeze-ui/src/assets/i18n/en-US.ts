import app from './en-US/app-common';
import pages from './en-US/page';
import start from './en-US/start';
import personalize from './en-US/personalize';
import authGuard from './en-US/auth-guard';
import footer from './en-US/footer';
import header from './en-US/header';
import login from './en-US/login';
import sideSetting from './en-US/side-setting';
import admin from './en-US/admin';

export default {
  ...app,
  ...pages,
  ...admin,
  ...start,
  ...personalize,
  ...authGuard,
  ...footer,
  ...header,
  ...login,
  ...sideSetting,
};
