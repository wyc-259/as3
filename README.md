# 实验 3：Android 界面组件

## 一、实验目的

1. 掌握 `ListView` 与 `SimpleAdapter` 的使用方法；
2. 掌握自定义布局的 `AlertDialog` 的创建方法；
3. 掌握使用 XML 定义菜单的方法；
4. 掌握上下文操作模式（ActionMode）上下文菜单的创建方法；
5. 掌握 `RecyclerView`、`Adapter` 与 `ViewHolder` 的使用方法。

## 二、实验环境

| 项目 | 版本 |
|------|------|
| 开发工具 | Android Studio |
| 开发语言 | Kotlin |
| compileSdk / targetSdk | 37 |
| minSdk | 24 |
| Android Gradle Plugin | 9.3.0 |
| Gradle | 9.5.0 |
| 主要依赖 | androidx.appcompat:appcompat:1.7.0、androidx.recyclerview:recyclerview:1.3.2 |

## 三、项目结构

```
app/src/main/
├── AndroidManifest.xml                 # 注册 5 个实验 Activity + 通知权限
├── java/com/example/as3/
│   ├── MainActivity.kt                 # 入口：导航到 5 个实验界面
│   ├── ListViewActivity.kt             # 任务 1：ListView + SimpleAdapter
│   ├── AlertDialogActivity.kt          # 任务 2：自定义 AlertDialog
│   ├── MenuActivity.kt                 # 任务 3：XML 定义菜单
│   ├── ActionModeActivity.kt           # 任务 4：ActionMode 上下文菜单
│   ├── RecyclerViewActivity.kt         # 任务 5：RecyclerView 列表
│   └── AnimalAdapter.kt                # 任务 5：Adapter + ViewHolder
└── res/
    ├── drawable/                       # 动物图片资源（cat/dog/elephant/lion/monkey/tiger）
    ├── layout/                         # 各界面布局 + 列表项 + 自定义对话框布局
    ├── menu/                           # menu_font.xml、menu_actionmode.xml
    └── values/                         # 主题、颜色、字符串
```

## 四、实验内容与实现

### 任务 1：ListView 的用法（SimpleAdapter）

**要求**：利用 `SimpleAdapter` 实现列表；注意列表项布局；图片使用附件动物图片资源；单击后用 Toast 显示选中的列表项信息；单击列表项后发送一条通知（图标为应用图标，Title 为列表项内容，通知内容自拟）。

**实现**：

- 数据源为 `List<Map<String, Object>>`，每个列表项包含 `image`、`name`、`desc` 三个键；
- 自定义列表项布局 `list_item.xml`（左侧图片 + 右侧名称与描述）；
- 通过 `SimpleAdapter` 将数据绑定到 `ListView`；
- 单击列表项弹出 Toast，并发送通知。

```kotlin
val adapter = SimpleAdapter(
    this, listItems, R.layout.list_item,
    arrayOf("image", "name", "desc"),
    intArrayOf(R.id.iv_animal, R.id.tv_name, R.id.tv_desc)
)
listView.adapter = adapter

listView.setOnItemClickListener { _, _, position, _ ->
    Toast.makeText(this, "选中了：$name（${descs[position]}）", Toast.LENGTH_SHORT).show()
    sendNotification(name)
}
```

**通知**：使用 `NotificationCompat.Builder`，小图标设为应用图标 `R.mipmap.ic_launcher`，标题为列表项内容；针对 Android 8.0+ 创建了 `NotificationChannel`，针对 Android 13+ 申请 `POST_NOTIFICATIONS` 运行时权限。

### 任务 2：创建自定义布局的 AlertDialog

**要求**：创建自定义布局对话框，调用 `AlertDialog.Builder.setView()` 将布局添加到 AlertDialog。

**实现**：

- 自定义布局 `dialog_login.xml`（图片 + 用户名/密码输入框 + 确定/取消按钮）；
- 通过 `layoutInflater.inflate` 加载布局，再调用 `setView()` 设置。

```kotlin
val view = layoutInflater.inflate(R.layout.dialog_login, null)
val dialog = AlertDialog.Builder(this).setView(view).create()
dialog.show()
```

### 任务 3：使用 XML 定义菜单

**要求**：字体大小（小/中/大，对应 10/16/20 号字），点击后设置测试文本字体；普通菜单项点击后弹出 Toast；字体颜色（红/黑）。

**实现**：

- 菜单文件 `menu/menu_font.xml`，使用嵌套子菜单与单选组；
- 在 `onOptionsItemSelected` 中根据菜单项 id 设置测试文本的字体大小与颜色。

```kotlin
when (item.itemId) {
    R.id.font_10 -> tv.textSize = 10f
    R.id.font_16 -> tv.textSize = 16f
    R.id.font_20 -> tv.textSize = 20f
    R.id.menu_normal -> Toast.makeText(this, "普通菜单项被点击", Toast.LENGTH_SHORT).show()
    R.id.color_red -> tv.setTextColor(getColor(R.color.red))
    R.id.color_black -> tv.setTextColor(getColor(R.color.black))
}
```

### 任务 4：创建上下文操作模式（ActionMode）的上下文菜单

**要求**：使用 ListView 创建列表，为列表项创建 ActionMode 形式的上下文菜单。

**实现**：

- 长按列表项调用 `startActionMode` 进入上下文操作模式；
- 通过 `ActionMode.Callback` 加载 `menu_actionmode.xml`（编辑/删除菜单项）；
- 点击「删除」后从列表移除该项并刷新，点击「编辑」弹出 Toast。

```kotlin
listView.setOnItemLongClickListener { _, _, position, _ ->
    if (actionMode == null) {
        actionMode = startActionMode(ActionModeCallback(position))
    }
    true
}
```

### 任务 5：使用 RecyclerView 实现列表视图效果

**要求**：列表项包含图片、名称；使用 `RecyclerView`、`Adapter` 与 `ViewHolder` 展示数据；单击显示名称；增加一条记录后列表立即更新；验证滚动正常、点击对象正确、更新后无错位。

**实现**：

- 自定义 `AnimalAdapter`，内部定义 `AnimalViewHolder` 复用 item 视图，避免滚动错位；
- `onBindViewHolder` 中按 position 绑定数据，保证更新后无错位；
- 单击列表项弹出 Toast 显示名称；
- 点击「添加一条记录」后 `notifyItemInserted` 局部刷新列表。

```kotlin
class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ivAnimal: ImageView = itemView.findViewById(R.id.iv_animal)
    val tvName: TextView = itemView.findViewById(R.id.tv_name)
}

override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
    val animal = data[position]
    holder.ivAnimal.setImageResource(animal.imageRes)
    holder.tvName.text = animal.name
    holder.itemView.setOnClickListener { onClick(animal) }
}
```

## 五、运行说明

1. 使用 Android Studio 打开项目，等待 Gradle 同步完成；
2. 连接真机或启动模拟器（Android 7.0 及以上）；
3. 点击 Run 运行，主界面显示 5 个实验入口按钮，分别点击进入对应实验；
4. 任务 1 首次点击列表项时会请求通知权限，授权后即可收到通知。

## 六、实验总结

通过本次实验，掌握了 Android 中常用界面组件的使用方法：`ListView` 配合 `SimpleAdapter` 展示复杂列表数据并处理点击事件与通知；通过 `AlertDialog.Builder.setView()` 创建自定义对话框；使用 XML 资源文件定义菜单并处理选中事件；通过 `ActionMode` 实现上下文操作模式菜单；使用 `RecyclerView` 的 `Adapter`/`ViewHolder` 模式高效展示可动态更新的列表。这些组件是 Android 界面开发的基础，为后续更复杂的界面开发打下了基础。
