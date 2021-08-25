export default {
  app: {
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
      },
      error: {
        formValidateError: '表单验证失败',
      },
      validate: {
        characterWord: '只能输入字母数字或下划线',
      },
    },
  },
};
