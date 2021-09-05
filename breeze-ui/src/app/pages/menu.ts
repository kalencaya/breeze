export default function (values) {
  return [
    {
      title: values['admin']['title'],
      link: '/admin',
      menuIcon: 'icon icon-setting',
      children: [
        {
          title: values['admin']['user'],
          link: '/breeze/admin/user',
        },
        {
          title: values['admin']['privilege'],
          link: '/breeze/admin/privilege',
        },
        {
          title: values['admin']['dict'],
          link: '/breeze/admin/dict',
        },
        {
          title: values['admin']['setting'],
          link: '/breeze/admin/setting',
        },
      ],
    },
  ];
}
