export default function (values) {
  return [
    // {
    //   title: values['dashboard']['title'],
    //   children: [
    //     {
    //       title: values['dashboard']['analysis'],
    //       link: '/pages/dashboard/analysis',
    //     },
    //   ],
    //   link: '/pages/dashboard',
    //   menuIcon: 'icon icon-console',
    // },
    {
      title: values['admin']['title'],
      link: '/admin',
      menuIcon: 'icon icon-setting',
      children: [
        {
          title: values['admin']['dict'],
          link: '/admin/dict',
        },
      ],
    },
  ];
}
