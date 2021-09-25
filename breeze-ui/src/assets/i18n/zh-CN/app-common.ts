export default {
  app: {
    title: {
      short: 'Breeze',
      long: 'Breeze 数据管理系统',
    },
    common: {
      loading: '加载中...',
      operate: {
        label: '操作',
        new: {
          label: '新增',
          title: '新增{{name}}',
          success: '新增成功',
        },
        edit: {
          label: '修改',
          title: '修改{{name}}',
          success: '修改成功',
        },
        delete: {
          label: '删除',
          title: '删除{{name}}',
          success: '删除成功',
          confirm: {
            title: '确认删除?',
            content: '数据删除后不可恢复，请谨慎操作！',
          },
        },
        query: {
          label: '查询',
          tip: '搜索',
        },
        reset: {
          label: '重置',
        },
        confirm: {
          label: '确认',
        },
        cancel: {
          label: '取消',
        },
        close: {
          label: '关闭',
        },
        forbid: {
          label: '注销',
          title: '注销{{name}}',
          success: '注销成功',
          confirm: {
            title: '确认注销?',
            content: '注销后用户不可登录，数据不会物理删除',
          },
        },
        enable: {
          label: '启用',
        },
        grant: {
          label: '授权',
          title: '{{name}}授权',
        },
      },
      error: {
        label: '错误',
        formValidateError: '表单验证失败',
        noLogin: '请先进行登录！',
        expired: '登录用户信息过期，请重新登录！',
      },
      validate: {
        characterWord: '只能输入字母数字或下划线',
        sameUserName: '用户名已存在',
        sameEmail: '邮箱已存在',
        samePassword: '两次输入的密码不一致',
        patternPassword: '密码格式错误，请输入数字和字母组成的字符串',
      },
    },
  },
};
