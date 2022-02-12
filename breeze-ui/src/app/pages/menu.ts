import { Privilege } from '../@core/data/admin.data';
import { PRIVILEGE_CODE, USER_AUTH } from '../@core/data/app.data';
interface Menu {
  title: string;
  link: string;
  menuIcon?: string;
  pCode?: string;
  children?: Menu[];
}

function hasMenu(code: string, pCodes: string[]): boolean {
  if (pCodes != null && pCodes != undefined) {
    return pCodes.includes(USER_AUTH.roleSysAdmin) || pCodes.includes(code);
  } else {
    return false;
  }
}
export default function (values) {
  let menu: Menu[] = [];
  let pCodes: string[] = JSON.parse(localStorage.getItem(USER_AUTH.pCodes));
  //studio
  let studioMemu: Menu = {
    title: values['studio']['title'],
    link: '/breeze/studio',
    menuIcon: 'icon icon-build-with-tool',
    pCode: PRIVILEGE_CODE.metaShow,
    children: [],
  };
  if (hasMenu(PRIVILEGE_CODE.datasourceShow, pCodes)) {
    studioMemu.children.push({
      title: values['studio']['job'],
      link: '/breeze/studio/job',
      pCode: PRIVILEGE_CODE.datasourceShow,
    });
  }
  if (hasMenu(PRIVILEGE_CODE.metaShow, pCodes)) {
    menu.push(studioMemu);
  }
  // meta menu
  let metaMenu: Menu = {
    title: values['meta']['title'],
    link: '/breeze/meta',
    menuIcon: 'icon icon-classroom-post-answers-large',
    pCode: PRIVILEGE_CODE.metaShow,
    children: [],
  };
  if (hasMenu(PRIVILEGE_CODE.datasourceShow, pCodes)) {
    metaMenu.children.push({
      title: values['meta']['datasource'],
      link: '/breeze/meta/datasource',
      pCode: PRIVILEGE_CODE.datasourceShow,
    });
  }
  if (hasMenu(PRIVILEGE_CODE.metaDataElementShow, pCodes)) {
    metaMenu.children.push({
      title: values['meta']['dataElement'],
      link: '/breeze/meta/dataElement',
      pCode: PRIVILEGE_CODE.metaDataElementShow,
    });
  }
  if (hasMenu(PRIVILEGE_CODE.metaRefDataShow, pCodes)) {
    metaMenu.children.push({
      title: values['meta']['refdata'],
      link: '/breeze/meta/refdata',
      pCode: PRIVILEGE_CODE.metaRefDataShow,
    });
  }
  if (hasMenu(PRIVILEGE_CODE.metaSystemShow, pCodes)) {
    metaMenu.children.push({
      title: values['meta']['system'],
      link: '/breeze/meta/system',
      pCode: PRIVILEGE_CODE.metaSystemShow,
    });
  }
  if (hasMenu(PRIVILEGE_CODE.metaShow, pCodes)) {
    menu.push(metaMenu);
  }
  // admin menu
  let adminMenu: Menu = {
    title: values['admin']['title'],
    link: '/breeze/admin',
    menuIcon: 'icon icon-setting',
    pCode: PRIVILEGE_CODE.adminShow,
    children: [],
  };
  if (hasMenu(PRIVILEGE_CODE.userShow, pCodes)) {
    adminMenu.children.push({
      title: values['admin']['user'],
      link: '/breeze/admin/user',
      pCode: PRIVILEGE_CODE.userShow,
    });
  }
  if (hasMenu(PRIVILEGE_CODE.privilegeShow, pCodes)) {
    adminMenu.children.push({
      title: values['admin']['privilege'],
      link: '/breeze/admin/privilege',
      pCode: PRIVILEGE_CODE.privilegeShow,
    });
  }
  if (hasMenu(PRIVILEGE_CODE.dictShow, pCodes)) {
    adminMenu.children.push({
      title: values['admin']['dict'],
      link: '/breeze/admin/dict',
      pCode: PRIVILEGE_CODE.dictShow,
    });
  }
  if (hasMenu(PRIVILEGE_CODE.settingShow, pCodes)) {
    adminMenu.children.push({
      title: values['admin']['setting'],
      link: '/breeze/admin/setting',
      pCode: PRIVILEGE_CODE.settingShow,
    });
  }

  if (hasMenu(PRIVILEGE_CODE.adminShow, pCodes)) {
    menu.push(adminMenu);
  }
  return menu;
}
