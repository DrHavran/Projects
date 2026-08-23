Repo for projects I am actually *proud* of / not school projects

I will probably try to refactor most of these since they are 1-2 years old...

**How to clone only a specific folder:**

```bash
    git clone --depth 1 --filter=blob:none --sparse https://github.com/DrHavran/School.git
    cd School
    git sparse-checkout set <folder-name>
```