import app from './zh-CN/app-common';
import pages from './zh-CN/page';
import start from './zh-CN/start';
import personalize from './zh-CN/personalize';
import authGuard from './zh-CN/auth-guard';
import footer from './zh-CN/footer';
import header from './zh-CN/header';
import login from './zh-CN/login';
import sideSetting from './zh-CN/side-setting';
import admin from './zh-CN/admin';

export default {
  ...app,
  ...pages,
  ...start,
  ...personalize,
  ...authGuard,
  ...footer,
  ...header,
  ...login,
  ...sideSetting,
  ...admin,
};
