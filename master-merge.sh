git pull
git checkout master
git pull
git merge develop
git push origin master
echo '************************************合并后的master版本号:*************************************'
git rev-parse HEAD
git checkout develop
